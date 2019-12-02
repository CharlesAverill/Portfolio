import matplotlib.pyplot as plt
import numpy as np
import os

from tensorflow import keras
from PIL import Image

from keras.preprocessing.image import ImageDataGenerator

import cv2

def decode(predictions, files):
    out = []
    count = 0
    for pred in predictions:
        if(int(pred[0][0]) == 1):
            out.append((files[count], "Car"))
        elif(int(pred[0][0]) == 0):
            out.append((files[count], "Airplane"))
        count += 1
    return out

model = "./models/" + input("Which model? ") + ".h5"

model = keras.models.load_model(model)

pics = int(input("How many pictures? "))

predictions = []
files = []

if(pics > 1):
    dir = input("Please enter image directory: ")
    arr = []
    for root, _, fils in os.walk(dir):
        files = fils
        for file in fils:
            try:
                print(file)
                img = cv2.imread(os.path.join(root, file))
                img = cv2.resize(img, (150, 150))
                img = img.reshape((1, 150, 150, 3)).astype(float)

                predictions.append(model.predict(img))
            except Exception as e:
                print(str(e))

else:
    image_dir = input("Please input image path: ")

    image = cv2.imread(image_dir)
    image = cv2.resize(image, (150, 150))
    image = image.reshape((1, 150, 150, 3)).astype(float)

    predictions.append(model.predict(image))

out = decode(predictions, files)

for o in out:
    print(o)
