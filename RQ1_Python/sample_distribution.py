import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\samplecommits.csv')

long = data[data['useJmhSince'] >= 1]['commits']
short = data[data['useJmhSince'] < 1]['commits']

valuesLong, base = np.histogram(long, range=[0, 65], bins=65)
valuesShort, base = np.histogram(short, range=[0, 65], bins=65)

label = np.arange(0, 65, 1)

plt.bar(label, valuesShort, label="short-lived projects", color="green", bottom=valuesLong)
plt.bar(label, valuesLong, label="long-lived projects", color="orange")
plt.ylabel('# projects')
plt.xlabel('# sample commits')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\sample_commits.pdf')

print("short -> avg: " + str(np.average(short).round(1)))
print("short -> std: " + str(np.std(short).round(1)))
print("short -> median: " + str(np.median(short)))

print("long -> avg: " + str(np.average(long).round(1)))
print("long -> std: " + str(np.std(long).round(1)))
print("long -> median: " + str(np.median(long)))
print("long 50 or more: " + str(len(list(filter(lambda x: x >= 50, long)))))