import pickle
import numpy as np

import pandas as pd

#These parameters are from Novemeber 7, 2019 in Cypress Texas around 10:20 PM. It is very dark and stormy, so predicted radiation should be near 0
humidity = 91
temperature = 51
pressure = 30.25
UNIXTime = 1573189237
timeofday = 23
daylengthinseconds = 38940
#Inserter_v2 predicts 1.25247667 W/m2, which is a very reasonable prediction

labels = ['UNIXTime', 'Temperature', 'Pressure', 'Humidity', 'DayLength', 'TimeOfDay']

conditions = pd.DataFrame([[UNIXTime, temperature, pressure, humidity, daylengthinseconds, timeofday]], columns = labels)
conditions['UNIXTime'] = pd.to_datetime(conditions['UNIXTime'],unit='s')
conditions.set_index('UNIXTime',inplace=True) # index by UNIXTime

model_name = input("Which model to test? ")

filename = "./models/" + model_name + ".inserter"

with open(filename, 'rb') as file:
    model = pickle.load(file)

prediction = model.predict(conditions)

print(prediction)
