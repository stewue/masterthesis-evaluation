import pandas as pd

data = pd.read_csv('D:\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\pdf_min_100_iterations_1_second.csv', delimiter=';')
data['project'] = data['project'].fillna('')
data['commit'] = data['commit'].fillna('')
data['benchmark'] = data['benchmark'].fillna('')
data['param'] = data['param'].fillna('')

for index, row in data.iterrows():
    per90 = ""
    per95 = ""
    per98 = ""
    per99 = ""
    for x in range(6,100):
        if not per90 and row["i" + str(x)] > 0.9:
            per90 = str(x)

        if not per95 and row["i" + str(x)] > 0.95:
            per95 = str(x)

        if not per98 and row["i" + str(x)] > 0.98:
            per98 = str(x)

        if not per99 and row["i" + str(x)] > 0.99:
            per99 = str(x)
            break

    print(str(row['project']) + ";" + str(row['commit']) + ";" + str(row['benchmark']) + ";" + str(row['param']) + ";" + per90 + ";" + per95 + ";" + per98 + ";" + per99)