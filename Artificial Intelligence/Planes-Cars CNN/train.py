import matplotlib.pyplot as plt
import numpy as np
import tensorflow as tf

from tensorflow import keras
from keras.models import Sequential
from keras.layers import Dense, Conv2D, Flatten, Dropout, MaxPooling2D
from keras.preprocessing.image import ImageDataGenerator

import os

def build_model(h, w):
    model = Sequential()
    model.add(Conv2D(16, 3, padding='same', activation='relu', input_shape=(h, w, 3)))
    model.add(MaxPooling2D())
    model.add(Dropout(.2))
    model.add(Conv2D(32, 3, padding='same', activation='relu'))
    model.add(MaxPooling2D())
    model.add(Conv2D(64, 3, padding='same', activation='relu'))
    model.add(MaxPooling2D())
    model.add(Dropout(.2))

    model.add(Flatten())

    model.add(Dense(512, activation='relu'))
    model.add(Dense(1, activation='sigmoid'))

    return model

path = "./data/"

train_dir = os.path.join(path, 'train')
val_dir = os.path.join(path, 'validation')

train_planes = os.path.join(train_dir, 'airplane')
train_cars = os.path.join(train_dir, 'car')

val_planes = os.path.join(val_dir, 'airplane')
val_cars = os.path.join(val_dir, 'car')

num_plane_tr = len(os.listdir(train_planes))
num_car_tr = len(os.listdir(train_cars))

num_plane_val = len(os.listdir(val_planes))
num_car_val = len(os.listdir(val_cars))

total_train = num_plane_tr + num_car_tr
total_val = num_plane_val + num_car_val

batch_size = 128
epochs = 15
h = 150
w = 150

train_image_generator = ImageDataGenerator(rescale=1./255, horizontal_flip=True, rotation_range=45, zoom_range=.5)
val_image_generator = ImageDataGenerator(rescale=1./255)

train_data_gen = train_image_generator.flow_from_directory(batch_size=batch_size,
                                                           directory=train_dir,
                                                           shuffle=True,
                                                           target_size=(h, w),
                                                           class_mode='binary')
val_data_gen = val_image_generator.flow_from_directory(batch_size=batch_size,
                                                       directory=val_dir,
                                                       target_size=(h, w),
                                                       class_mode='binary')

model = build_model(h, w)

model.compile(optimizer='adam',
              loss='binary_crossentropy',
              metrics=['accuracy'])

history = model.fit_generator(train_data_gen,
                              steps_per_epoch=total_train // batch_size,
                              epochs=epochs,
                              validation_data=val_data_gen,
                              validation_steps=total_val // batch_size)

acc = history.history['accuracy']
val_acc = history.history['val_accuracy']

loss = history.history['loss']
val_loss = history.history['val_loss']

epochs_range = range(epochs)

plt.figure(figsize=(8, 8))
plt.subplot(1, 2, 1)
plt.plot(epochs_range, acc, label='Training Accuracy')
plt.plot(epochs_range, val_acc, label='Validation Accuracy')
plt.legend(loc='lower right')
plt.title('Training and Validation Accuracy')

plt.subplot(1, 2, 2)
plt.plot(epochs_range, loss, label='Training Loss')
plt.plot(epochs_range, val_loss, label='Validation Loss')
plt.legend(loc='upper right')
plt.title('Training and Validation Loss')
plt.show()

save_model = input("Do you want to save the model? (y/n) ")
if("y" in save_model.lower()):
    filename = "./models/" + input("Enter filename (no extension) ") + ".h5"
    model.save(filename)
