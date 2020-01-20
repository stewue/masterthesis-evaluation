import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionchange.csv')

filteredOlder = data[data['useJmhSince'] > 1]
older = filteredOlder['numberOfChanges']
valuesOlder, baseOlder = np.histogram(older, bins=[0, 1, 2, 3, 4, 5, 10, 27], weights=np.ones(len(older)) / len(data))

filteredYounger = data[data['useJmhSince'] <= 1]
younger = filteredYounger['numberOfChanges']
valuesYounger, baseYounger = np.histogram(younger, bins=[0,1,2,3,4,5,10,27], weights=np.ones(len(younger)) / len(data))

x = np.arange(7)

fig = plt.figure()
total = data.shape[0]

# absolute
ax1 = fig.add_subplot()
ax1.bar(x, valuesYounger * total, label="short-lived projects", color="green", bottom=valuesOlder * total)
ax1.bar(x, valuesOlder * total, label="long-lived projects", color="orange")
ax1.set_ylabel('# projects')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.bar(x, valuesYounger, color="green", bottom=valuesOlder)
ax2.bar(x, valuesOlder, color="orange")
ax2.set_ylabel('# projects [%]')

label = ('0', '1', '2', '3', '4', '5-9', '10-27')
ax1.legend()
plt.xticks(x, label)
ax1.set_xlabel('# JMH version updates')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\jmh_version_change.pdf')

print("long-lived avg: " + str(np.average(older)))
print("long-lived deviation: " + str(np.std(older)))
print("long-lived median: " + str(np.median(older)))
print("long-lived 75%: " + str(np.percentile(older, 75)))