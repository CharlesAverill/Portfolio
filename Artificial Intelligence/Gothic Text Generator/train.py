import numpy
import sys
import glob
import nltk
import pickle
import keras

import tensorflow as tf

from nltk.tokenize import RegexpTokenizer
from nltk.corpus import stopwords
from keras.models import Sequential
from keras.layers import Dense, Dropout, LSTM
from keras.utils import np_utils
from keras.callbacks import ModelCheckpoint

def tokenize_words(input):
    input = input.lower()

    tokenizer = RegexpTokenizer(r'\w+')
    tokens = tokenizer.tokenize(input)

    filtered = filter(lambda token: token not in stopwords.words('english'), tokens)

    return " ".join(filtered)

def build_model(x, y):
    model = Sequential()
    model.add(LSTM(256, input_shape=(x.shape[1], x.shape[2]), return_sequences=True))
    model.add(Dropout(.2))
    model.add(LSTM(256, return_sequences=True))
    model.add(Dropout(.2))
    model.add(LSTM(128))
    model.add(Dropout(.2))
    model.add(Dense(y.shape[1], activation='softmax'))
    model.compile(loss='categorical_crossentropy', optimizer='adam')
    return model

def train(model, x, y, filepath):
    checkpoint = ModelCheckpoint(filepath, monitor='loss', verbose=1, save_best_only=True, mode='min')
    desired_callbacks = [checkpoint]

    model.fit(x, y, epochs=4, batch_size=256, callbacks=desired_callbacks)

    return model

text = ""

if(not "./data/processed_inputs.txt" in glob.glob("./data/*.txt")):
    for book in glob.glob("./data/*.txt"):
        file = open(book).read()
        print(book)
        processed_inputs = tokenize_words(file)
        text += processed_inputs

    file = open("./data/processed_inputs.txt", "a")
    file.write(text)
    file.close()

text = open("./data/processed_inputs.txt").read()

chars = sorted(list(set(text)))
char_to_num = dict((c, i) for i, c, in enumerate(chars))

input_len = len(text)
vocab_len = len(chars)

seq_length = 100
trainf = []
trainl = []

for i in range(0, input_len - seq_length, 1):
    in_seq = text[i:i + seq_length]
    out_seq = text[i + seq_length]

    trainf.append([char_to_num[char] for char in in_seq])
    trainl.append(char_to_num[out_seq])

n_patterns = len(trainf)

qtrain = input("Want to train? ")


x = numpy.reshape(trainf, (n_patterns, seq_length, 1))
x = x / float(vocab_len)

y = np_utils.to_categorical(trainl)

model = build_model(x, y)

if("y" in qtrain):
    model = train(model, x, y, "./checkpoints/model.hdf5")

else:
    model = keras.models.load_model("./checkpoints/model.hdf5")
    model.load_weights("./checkpoints/model.hdf5")
    model.compile(loss='categorical_crossentropy', optimizer='adam')

    num_to_char = dict((i, c) for i, c in enumerate(chars))

    start = numpy.random.randint(0, len(trainf) - 1)
    pattern = trainf[start]
    print("Random seed:")
    print("\"", ''.join([num_to_char[value] for value in pattern]), "\"")

    for i in range(1000):
        x = numpy.reshape(pattern, (1, len(pattern), 1))
        x = x /float(vocab_len)
        prediction = model.predict(x, verbose=0)
        index = numpy.argmax(prediction)
        result = num_to_char[index]
        seq_in = [num_to_char[value] for value in pattern]

        sys.stdout.write(result)

        pattern.append(index)
        pattern = pattern[1:len(pattern)]
