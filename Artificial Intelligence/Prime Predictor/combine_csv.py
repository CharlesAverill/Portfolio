import pandas as pd
import glob

csv_folder = "./data/output.csv"

df = pd.DataFrame(columns=['Rank', 'Num', 'Interval'])

for csv in glob.glob(csv_folder):
    df1 = pd.read_csv(csv)
    print(csv)
    df = df.append(df1, sort=False)

df.to_csv("./data/output.csv", index=False)
