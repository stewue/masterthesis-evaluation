import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\codechanged.csv')
values = data['numberOfChanges']

valuesAll, base = np.histogram(values, bins=[0,1,2,3,4,5,9])
x = np.arange(6)

label = ('0', '1', '2', '3', '4', '5-8')
plt.bar(x, valuesAll)
plt.ylim(0,1700)
plt.xticks(x, label)
plt.xlabel('# code changes')
plt.ylabel('# benchmarks')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')