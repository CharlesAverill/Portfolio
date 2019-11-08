def getcol(df, index, isnumeric=False):
    if(isnumeric):
        return df.iloc[:,index].values.astype(float).reshape(-1,1)
    else:
        return df.iloc[:,index].values.reshape(-1,1)

#create normalized dataframe that contains training data
x_train = pd.DataFrame()

#Create MinMaxScaler for normalization
mm_scaler = preprocessing.MinMaxScaler()

#Store columns for preprocessing
UNIXTime, Date, Times, TimeSunRise, TimeSunSet = getcol(df, 0, True), getcol(df, 1), getcol(df, 2), getcol(df, 9), getcol(df, 10)
Radiation, Temperature, Pressure, Humidity, WindDirection, Speed = getcol(df, 3, True), getcol(df, 4, True), getcol(df, 5, True), getcol(df, 6, True), getcol(df, 7, True), getcol(df, 8, True)

#Fit and Normalize UNIXTime
mm_scaler.fit(UNIXTime)
UNIXTime_normal = pd.DataFrame(data=mm_scaler.transform(UNIXTime))

#Temperature
mm_scaler.fit(Temperature)
Temperature_normal = pd.DataFrame(data=mm_scaler.transform(Temperature))

#Pressure
mm_scaler.fit(Pressure)
Pressure_normal = pd.DataFrame(data=mm_scaler.transform(Pressure))

#Humidity
mm_scaler.fit(Humidity)
Humidity_normal = pd.DataFrame(data=mm_scaler.transform(Humidity))

#Wind Direction
mm_scaler.fit(WindDirection)
WindDirection_normal = pd.DataFrame(data=mm_scaler.transform(WindDirection))

#Speed
mm_scaler.fit(Speed)
Speed_normal = pd.DataFrame(data=mm_scaler.transform(Speed))

#Radiation - Don't add to the x_train because it's the value we're training the model to preict
mm_scaler.fit(Radiation)
Radiation_normal = pd.DataFrame(data=mm_scaler.transform(Radiation)).values

#Convert Radiation_normal to NumPy array
Radiation_normal = np.array(Radiation_normal)

#Concatenate all normalized values, set column names
x_train = pd.concat([UNIXTime_normal, Temperature_normal, Pressure_normal, Humidity_normal, WindDirection_normal, Speed_normal], axis=1)
x_train.columns=['UNIXTime', 'Temperature', 'Pressure', 'Humidity', 'Wind Direction(Degrees)', 'Speed']

#Create test data with first 3000 rows (10%)
test_data = x_train.iloc[0:3300]
x_train = x_train.iloc[3300:]
#Create expected test results array, remove test data from Radiation_normal, and convert Radiation_normal to a 1d array
test_results_real = Radiation_normal[:3300]
Radiation_normal = Radiation_normal[3300:].ravel()

#Convert x_train to NumPy array
x_train = x_train.to_numpy()

#Create and fit the MLP Regressor
mlp = MLPRegressor(hidden_layer_sizes=(30,30,30))
mlp.fit(x_train, Radiation_normal)

#Predict
predictions = mlp.predict(test_data)

print(confusion_matrix(test_results_real, predictions))
