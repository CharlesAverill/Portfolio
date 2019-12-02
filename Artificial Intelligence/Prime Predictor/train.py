import pandas as pd

import sklearn
import pickle

from sklearn import preprocessing
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split

def readdata(filepath):
    df = pd.read_csv(filepath).dropna()

    df['Rank'] = df['Rank'].astype(int)
    df['Num'] = df['Num'].astype(int)
    df['Interval'] = df['Interval'].astype(int)

    return df.drop(['Interval'], axis=1)

def train(features, labels):
    #Separate features and labels into training and testing sets
    ftrain, ftest, ltrain, ltest = train_test_split(features, labels, test_size=0.1)
    #Create Random Forest Regressor with 100 trees
    regressor = RandomForestRegressor(n_estimators=100, verbose=1)
    #Fit model and get accuracy
    model = regressor.fit(ftrain, ltrain)
    accuracy = regressor.score(ftest, ltest) * 100

    return model, accuracy

csv = "./data/1m.csv"
df = readdata(csv)

features = df.drop('Num', axis=1).values
labels = df['Num'].values

model, accuracy = train(features, labels)
print("Accuracy:", accuracy)

save = input("Do you want to save the model? ")
if("y" in save):
    dir = "./models/"
    ext = ".prime"
    model_name = input("Please enter model name (no extension): ")
    path = "./models/" + model_name + ext

    pickle.dump(model, open(path, 'wb'))

    print("Model saved to " + path +  " with accuracy " + str(accuracy) + "%")
