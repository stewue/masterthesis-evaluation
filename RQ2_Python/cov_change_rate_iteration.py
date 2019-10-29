import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\Documents\\cov_change_rate_100_iterations_1_second.csv', delimiter=';')
data['project'] = data['project'].fillna('')
data['commit'] = data['commit'].fillna('')
data['benchmark'] = data['benchmark'].fillna('')
data['params'] = data['params'].fillna('')

for index, row in data.iterrows():
    per10 = ""
    per5 = ""
    per2 = ""
    per1 = ""
    for x in range(5,100):
        if not per10 and row["i" + str(x)] < 0.1:
            per10 = str(x)

        if not per5 and row["i" + str(x)] < 0.05:
            per5 = str(x)

        if not per2 and row["i" + str(x)] < 0.02:
            per2 = str(x)

        if not per1 and row["i" + str(x)] < 0.01:
            per1 = str(x)
            break

    print(str(row['project']) + ";" + str(row['commit']) + ";" + str(row['benchmark']) + ";" + str(row['params']) + ";" + per10 + ";" + per5 + ";" + per2 + ";" + per1)