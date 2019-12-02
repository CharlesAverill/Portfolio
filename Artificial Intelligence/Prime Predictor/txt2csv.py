import pandas as pd

with open("./data/P-1000000.txt", "r") as file:

    lines = file.readlines()

columns = ['Rank', 'Num', 'Interval']
df = pd.DataFrame(columns=columns)

count = 0
for line in lines:
    count += 1
    if(count % 1000 == 0):
        print(count)
    elements = line.split(", ")
    data = [[elements[0], elements[1], elements[2][:-1]]]
    df1 = pd.DataFrame(data = data, columns=columns)
    df = df.append(df1)

print(df)
df.to_csv("./data/P-1000000.csv")
