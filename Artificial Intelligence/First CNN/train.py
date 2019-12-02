from keras.datasets import mnist
from keras.layers import Dense, Conv2D, Flatten
from keras.models import Sequential
from keras.utils import to_categorical

import matplotlib.pyplot as plt

def build_model(ksize=3):
    model = Sequential()
    #Creates two Convolution layers to take in input
    model.add(Conv2D(64, kernel_size=ksize, activation='relu', input_shape=(28, 28, 1)))
    model.add(Conv2D(32, kernel_size=ksize, activation='relu'))
    #Add Flatten layer to connect Conv2D layers and Dense layers
    model.add(Flatten())
    #Output layer, 10 nodes for 10 possible outputs (0-9)
    model.add(Dense(10, activation='softmax'))

    return model

#Load MNIST dataset into training and testing features and labels
(trainfeatures, trainlabels), (testfeatures, testlabels) = mnist.load_data()

#Reshape in format of length, imwidth, imheight, grayscale=true
trainfeatures = trainfeatures.reshape(60000, 28,  28, 1)
testfeatures = testfeatures.reshape(10000, 28, 28, 1)

#One-hot encodes the labels (converts them into binary vectors to denote class)
trainlabels = to_categorical(trainlabels)
testlabels = to_categorical(testlabels)

#Build model
model = build_model()

#Compile model. 'categorical_crossentropy' is built for categorical classification, which is what I'm trying to do with this model (categories are 0-9)
model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

#Train model
model.fit(trainfeatures, trainlabels, validation_data=(testfeatures, testlabels), epochs=1)

#Save model
save = input("Save model?(y/n) ")
if("y" in save or "Y" in save):
    filename = input("Please enter filename without extension: ")
    model.save("./models/" + filename + ".h5")
    print("Model saved to", ("./models/" + filename + ".h5"))
