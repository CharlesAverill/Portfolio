"""
Author: Matthew Charles Averill
My first handwritten ML model. Very simple, just some regression. But I'm very proud of it, and this project fulfilled a goal I've had for a very long time
"""

import pandas as pd
import matplotlib.pyplot as plt

import numpy as np
import sklearn

import pytz

import pickle

from sklearn import preprocessing
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split

def readdata(csv):
    #read in csv
    df = pd.read_csv(csv)

    #Set units, formats, datatypes
    df['UNIXTime'] = pd.to_datetime(df['UNIXTime'],unit='s')
    df['Radiation'] = df['Radiation'].astype(float)
    df['Temperature'] = df['Temperature'].astype(float)
    df['Pressure'] = df['Pressure'].astype(float)
    df['Humidity'] = df['Humidity'].astype(int)
    df['WindDirection(Degrees)'] = df['WindDirection(Degrees)'].astype(float)
    df['Speed'] = df['Speed'].astype(float)
    df['TimeSunRise'] = pd.to_datetime(df['TimeSunRise'],format='%H:%M:%S')
    df['TimeSunSet'] = pd.to_datetime(df['TimeSunSet'],format='%H:%M:%S')
    df.rename(columns={'WindDirection(Degrees)': 'WindDirection', 'Speed': 'WindSpeed'}, inplace=True)

    #Calculate length of day in seconds as a feature, get rid of TimeSunRise, TimeSunSet
    df['DayLength'] = (df['TimeSunSet']-df['TimeSunRise'])/np.timedelta64(1, 's')

    #These are rendunant or useless, drop them
    df.drop(['TimeSunRise','TimeSunSet', 'Data', 'Time'],axis=1,inplace=True)

    #Re-sort based on time
    df.sort_values('UNIXTime', inplace=True) # sort by UNIXTime
    df.set_index('UNIXTime',inplace=True) # index by UNIXTime

    #Times are stored in Hawaii timezone, so localize all UNIXTimes to Hawaii
    hawaii = pytz.timezone('Pacific/Honolulu')
    df.index = df.index.tz_localize(pytz.utc).tz_convert(hawaii)

    return df

def train(features, labels):
    #Separate features and labels into training and testing sets
    ftrain, ftest, ltrain, ltest = train_test_split(features, labels, test_size=0.1)

    regressor = RandomForestRegressor(n_estimators=100)

    model = regressor.fit(ftrain, ltrain)
    accuracy = regressor.score(ftest, ltest) * 100

    return model, accuracy

csv = "./SolarPrediction.csv"

df = readdata(csv)

#Add TimeOfDay as a feature
df['TimeOfDay'] = df.index.hour

#Remove Wind Direction and speed, they don't correlate
df.drop(['WindDirection','WindSpeed'], axis=1, inplace=True)

#Create features and label matrices for training
features = df.drop('Radiation', axis=1).values
labels = df['Radiation'].values

#Train model and find its accuracy
model, accuracy = train(features, labels)

#Set directory, extension, and request model name
dir = "./models/"
ext = ".inserter"
model_name = input("Please enter model name (no extension): ")
path = "./models/" + model_name + ext

#save model with .inserter extension
pickle.dump(model, open(path, 'wb'))

print("Model saved to " + path +  " with accuracy " + str(accuracy) + "%")
