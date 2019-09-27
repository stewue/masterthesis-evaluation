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
label = ('0', '1', '2', '3', '4', '5-9', '10+')
plt.bar(x, valuesOlder, label="Older")
plt.bar(x, valuesYounger, label="Younger", bottom=valuesOlder)
plt.legend()
plt.xticks(x, label)
plt.xlabel('numberOfJmhVersionChanges')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')