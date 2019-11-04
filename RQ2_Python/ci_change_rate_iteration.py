import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\ci_change_rate_100_iterations_1_second.csv', delimiter=';')
data['project'] = data['project'].fillna('')
data['commit'] = data['commit'].fillna('')
data['benchmark'] = data['benchmark'].fillna('')
data['params'] = data['params'].fillna('')

for index, row in data.iterrows():
    per5 = ""
    per3 = ""
    per2 = ""
    per1 = ""
    per05 = ""
    for x in range(5,100):
        if not per5 and row["i" + str(x)] < 0.05:
            per5 = str(x)

        if not per3 and row["i" + str(x)] < 0.03:
            per3 = str(x)

        if not per2 and row["i" + str(x)] < 0.02:
            per2 = str(x)

        if not per1 and row["i" + str(x)] < 0.01:
            per1 = str(x)
            break

    print(str(row['project']) + ";" + str(row['commit']) + ";" + str(row['benchmark']) + ";" + str(row['params']) + ";" + per5 + ";" + per3 + ";" + per2 + ";" + per1)