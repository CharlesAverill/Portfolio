import matplotlib.pyplot as plt
import pandas as pd

from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import train_test_split

def process_age(df, cut_points, label_names):
    df['Age'] = df['Age'].fillna(-.5)
    df['Age_categories'] = pd.cut(df['Age'], cut_points, labels=label_names)

    return df

def create_dummies(df, column_name):
    dummies = pd.get_dummies(df[column_name], prefix=column_name)
    df = pd.concat([df, dummies], axis=1)
    return df

def train_model(train, test, columns):
    lr = LogisticRegression()
    lr.fit(train[columns], test)
    lr.decision_function(train[columns])
    return lr

train = pd.read_csv("./data/train.csv")
test = pd.read_csv("./data/test.csv")
cut_points = [-1, 0, 5, 12, 18, 35, 60, 100]
label_names = ['Missing', 'Infant', 'Child', 'Teenager', 'Young Adult', 'Adult', 'Senior']

train = process_age(train, cut_points, label_names)
test = process_age(test, cut_points, label_names)

age_cat_pivot = train.pivot_table(index='Age_categories', values='Survived')

train = create_dummies(train, 'Pclass')
test = create_dummies(test, 'Pclass')

train = create_dummies(train, 'Sex')
train = create_dummies(train, 'Age_categories')
test = create_dummies(test, 'Sex')
test = create_dummies(test, 'Age_categories')

holdout = test

columns = ['Pclass_1', 'Pclass_2', 'Pclass_3', 'Sex_female', 'Sex_male',
       'Age_categories_Missing','Age_categories_Infant',
       'Age_categories_Child', 'Age_categories_Teenager',
       'Age_categories_Young Adult', 'Age_categories_Adult',
       'Age_categories_Senior']

features = train[columns]
labels = train['Survived']

trainf, testf, trainl, testl = train_test_split(features, labels, test_size=.2, random_state=0)

model = train_model(features, labels, columns)

predictions = model.predict(holdout[columns])

holdout_ids = holdout['PassengerId']
submission_df = {'PassengerId': holdout_ids, 'Survived': predictions}

submission = pd.DataFrame(submission_df)

submission.to_csv('titanic_submission.csv', index=False)
