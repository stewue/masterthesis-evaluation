import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionchange.csv')

filteredOlder = data[data['useJmhSince'] > 1]
older = filteredOlder['numberOfChanges']
valuesOlder, baseOlder = np.histogram(older, bins=[0,1,2,3,4,5,10,27], weights=np.ones(len(older)) / len(data))

filteredYounger = data[data['useJmhSince'] <= 1]
younger = filteredYounger['numberOfChanges']
valuesYounger, baseYounger = np.histogram(younger, bins=[0,1,2,3,4,5,10,27], weights=np.ones(len(younger)) / len(data))

x = np.arange(7)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
label = ('0', '1', '2', '3', '4', '5-9', '10-27')
plt.bar(x, valuesYounger, label="short-lived projects",  color="green", bottom=valuesOlder)
plt.bar(x, valuesOlder, label="long-lived projects", color="orange")
plt.legend()
plt.xticks(x, label)
plt.ylabel('probability')
plt.xlabel('# JMH version updates')
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')

print("long-lived avg: " + str(np.average(older)))
print("long-lived deviation: " + str(np.std(older)))
print("long-lived median: " + str(np.median(older)))
print("long-lived 75%: " + str(np.percentile(older, 75)))