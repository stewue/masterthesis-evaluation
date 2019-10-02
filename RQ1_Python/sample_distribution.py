import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\history\\history-selected-commits.csv')

grouped = data.groupby('project')

long = []
short = []

for project, group in grouped:
    time = (group.iloc[0, 1] - group.iloc[-1, 1]) / 31536000.0

    if time >= 1:
        long.append(group.shape[0])
    else:
        short.append(group.shape[0])

valuesLong, base = np.histogram(long, range=[0,65], bins=65)
valuesShort, base = np.histogram(short, range=[0,65], bins=65)

label = np.arange(0,65,1)

plt.bar(label, valuesLong, label="long-lived", color="orange")
plt.bar(label, valuesShort, label="short-lived", color="green", bottom=valuesLong)
plt.ylabel('# projects')
plt.xlabel('# sample commits')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('export.png')

print("short -> avg: " + str(np.average(short).round(1)))
print("short -> median: " + str(np.median(short)))

print("long -> avg: " + str(np.average(long).round(1)))
print("long -> median: " + str(np.median(long)))
print("long 60 or more: " + str(len(list(filter(lambda x: x >= 60, long)))))