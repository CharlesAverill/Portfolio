import gym
import random

import numpy as np

from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from tensorflow.keras.optimizers import Adam

def model_data_preparation(initial_games, goal_steps):
    training_data = []
    accepted_scores = []
    for game_index in range(initial_games):
        score = 0
        game_memory = []
        previous_observation = []
        for step_index in range(goal_steps):
            action = random.randrange(0, 2)
            observation, reward, done, info = env.step(action)

            if len(previous_observation) > 0:
                game_memory.append([previous_observation, action])

            previous_observation = observation

            score += reward

            if done:
                break

        if score >= score_requirement:
            accepted_scores.append(score)
            for data in game_memory:
                if data[1] == 1:
                    output = [0, 1]
                elif data[1] == 0:
                    output = [1, 0]
                training_data.append([data[0], output])

        env.reset()

    print(accepted_scores)

    return training_data

def build_model(input_size, output_size):
    model = Sequential([
        Dense(128, input_dim=input_size, activation='relu'),
        Dense(52, activation='relu'),
        Dense(output_size, activation='linear')])
    model.compile(loss='mse', optimizer=Adam())

    return model

def train_model(trianing_data):
    x = np.array([i[0] for i in training_data]).reshape(-1, len(training_data[0][0]))
    y = np.array([i[1] for i in training_data]).reshape(-1, len(training_data[0][1]))
    model = build_model(len(x[0]), len(y[0]))

    model.fit(x, y, epochs = 10)

    return model

env = gym.make('CartPole-v1')
env.reset()

goal_steps = 500
score_requirement = 60
initial_games = 30000

training_data = model_data_preparation(initial_games, goal_steps)
model = train_model(training_data)

scores = []
choices = []
for each_game in range(100):
    score = 0
    prev_obs = []
    for step_index in range(goal_steps):
        env.render()
        if len(prev_obs)==0:
            action = random.randrange(0,2)
        else:
            action = np.argmax(model.predict(prev_obs.reshape(-1, len(prev_obs)))[0])

        choices.append(action)
        new_observation, reward, done, info = env.step(action)
        prev_obs = new_observation
        score+=reward
        if done:
            break

    env.reset()
    scores.append(score)

print(scores)
print('Average Score:', sum(scores)/len(scores))
print('choice 1:{}  choice 0:{}'.format(choices.count(1)/len(choices),choices.count(0)/len(choices)))

env.close()
