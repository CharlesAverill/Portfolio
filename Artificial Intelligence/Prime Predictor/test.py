import pandas as pd

import sklearn
import pickle

from itertools import count, islice
from math import sqrt
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split

def isPrime(n):
    return n > 1 and all(n%i for i in islice(count(2), int(sqrt(n)-1)))

csv = pd.read_csv("./data/1m.csv").values

labels = ['Rank']

num = int(input("What index prime do you want? "))

model_name = input("Which model to test? ")

filename = "./models/" + model_name + ".prime"

with open(filename, 'rb') as file:
    model = pickle.load(file)

#Predicting
inp = pd.DataFrame([[num]], columns = labels)

prediction1 = int(round(model.predict(inp)[0] - .5))
prediction2 = prediction1
#Makes predictions more accurate
while(not isPrime(prediction1) and not isPrime(prediction2)):
    prediction1 += 1
    prediction2 -= 1

#Print values
if(isPrime(prediction1)):
    print("Prediction:", prediction1)
else:
    print("Prediction:", prediction2)
#Only prints actual value if the csv has it
if(num < len(csv)):
    print("Actual Value:", csv[num - 1][1])
