print(chr(27) + "[2J")

import PyPDF2
import pandas as pd
from os import listdir
import re
import scanner as S

class Question:

    round = 0

    qid = ""
    category = ""
    type = ""
    question = ""
    choices = list()
    tossanswer = ""
    mcanswer = ""

    bcategory = ""
    btype = ""
    bquestion = ""
    bchoices = list()
    btossanswer = ""
    bmcanswer = ""

for set in range(1, 11):
    folder = "./PDFs/" + str(set) + "/"
    files = listdir(folder)
    for fil in files:
        if(not ".pdf" in fil):
            continue
        pdf = open(folder + fil, 'rb')
        reader =  PyPDF2.PdfFileReader(pdf)

        for n in range(reader.numPages):
            page = reader.getPage(n)
            text = page.extractText()

            round = int(text[text.index("Round") + 6:text.index("Round") + 7])

            split = re.split(" TOSS\n-UP  ", text)
            split = split[1:]

            for question in split:
                file = S.Scanner(source=question, delim=" ")

                #print(question)

                q = Question()
                q.round = round

                q.qid = file.next()[:1]

                q.category = file.next_line()
                if(")" in q.category):
                    q.category = q.category[q.category.index(")")+2:]
                if(q.category is "ASTRO"):
                    q.category = "ASTRONOMY"
                if(len(q.category) < 2):
                    q.category = "Unknown"

                q.type = file.next_line()
                if(not("Short" in q.type or "Multiple" in q.type)):
                    q.type = file.next_line()

                line = ""
                total = ""
                while(not ("ANSWER" in line or "W)" in line)):
                    line = file.next_line().strip()
                    if(not ("ANSWER" in line or "W)" in line)):
                        total += line
                q.question = total
                if("W)" in line):
                    w = ""
                    x = ""
                    y = ""
                    z = ""

                    w = line

                    while(not "X)" in line):
                        line = file.next()
                        if(not "X)" in line):
                            w += line

                    w = w.strip().replace("\n", " ")

                    x = line

                    while(not "Y)" in line):
                        line = file.next() + " "
                        if(not "Y)" in line):
                            x += line

                    x = x.strip().replace("\n", "")

                    y = line

                    while(not "Z)" in line):
                        line = file.next() + " "
                        if(not "Z)" in line):
                            y += line

                    y = y.strip().replace("\n", "")

                    z = line

                    while(not "ANSWER:" in line):
                        line = file.next() + " "
                        if(not "ANSWER" in line):
                            z += line

                    z = z.strip().replace("\n", "")

                    ls = list()

                    ls.append(w)
                    ls.append(x)
                    ls.append(y)
                    ls.append(z)

                    q.choices = ls

                    ls = None

                    total = ""
                    while(not "BONUS" in line):
                        line = file.next() + " "
                        if(not "BONUS" in line):
                            total += line
                    q.mcanswer = total
                else:
                    total = line[9:]
                    while(not "BONUS" in line):
                        line = file.next() + " "
                        if(not "BONUS" in line):
                            total += line
                    q.tossanswer = total
                    print(q.tossanswer)
                #Bonus
                q.bcategory = file.next_line()
                if(")" in q.bcategory):
                    q.bcategory = q.bcategory[q.bcategory.index(")")+2:]
                if(q.bcategory is "ASTRO"):
                    q.bcategory = "ASTRONOMY"
                if(len(q.bcategory) < 1):
                    q.bcategory = "Unknown"

                q.btype = file.next_line()
                if(not("Short" in q.type or "Multiple" in q.btype)):
                    q.type = file.next_line()

                line = ""
                total = ""
                while(not ("ANSWER" in line or "W)" in line)):
                    line = file.next_line().strip()
                    if(not ("ANSWER" in line or "W)" in line)):
                        total += line
                q.bquestion = total
                if("W)" in line):
                    w = ""
                    x = ""
                    y = ""
                    z = ""

                    w = line

                    while(not "X)" in line):
                        line = file.next()
                        if(not "X)" in line):
                            w += line

                    w = w.strip().replace("\n", " ")

                    x = line

                    while(not "Y)" in line):
                        line = file.next() + " "
                        if(not "Y)" in line):
                            x += line

                    x = x.strip().replace("\n", "")

                    y = line

                    while(not "Z)" in line):
                        line = file.next() + " "
                        if(not "Z)" in line):
                            y += line

                    y = y.strip().replace("\n", "")

                    z = line

                    while(not "ANSWER:" in line):
                        line = file.next() + " "
                        if(not "ANSWER" in line):
                            z += line

                    z = z.strip().replace("\n", "")

                    ls = list()

                    ls.append(w)
                    ls.append(x)
                    ls.append(y)
                    ls.append(z)

                    q.choices = ls

                    ls = None

                    total = ""
                    while(file.has_next()):
                        line = file.next() + " "
                        if(not line is None):
                            total += line
                    q.bmcanswer = total
                else:
                    total = line[9:]
                    while(file.has_next()):
                        line = file.next() + " "
                        if(not line is None):
                            total += line
                    q.btossanswer = total
                print(q.bcategory + " " + q.btype + " " + q.bquestion + " " + str(q.bchoices) + " " + q.btossanswer + " " + q.bmcanswer)

                file.close()
