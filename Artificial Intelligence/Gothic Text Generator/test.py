import numpy
import sys
import glob
import nltk
import pickle

from nltk.tokenize import RegexpTokenizer
from nltk.corpus import stopwords
from keras.models import Sequential
from keras.layers import Dense, Dropout, LSTM
from keras.utils import np_utils
from keras.callbacks import ModelCheckpoint

filepath = "./checkpoints/model.hdf5"
model = Sequential()
model.load_weights(filepath)
model.compile(loss='categorical_crossentropy', optimizer='adam')

text = open("./data/processed_inputs.txt").read()

chars = sorted(list(set(text)))
char_to_num = dict((c, i) for i, c, in enumerate(chars))
num_to_char = dict((i, c) for i, c, in enumerate(chars))

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

start = numpy.random.randint(0, len(x))
