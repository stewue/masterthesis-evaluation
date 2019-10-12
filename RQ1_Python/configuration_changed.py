import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\configurationchanged.csv')
values = data['numberOfChanges']

valuesAll, base = np.histogram(values, bins=[0,1,2,3,4])
x = np.arange(4)

label = ('0', '1', '2', '3')
plt.bar(x, valuesAll)
plt.ylim(0,224)
plt.xticks(x, label)
plt.xlabel('# configuration changes')
plt.ylabel('# benchmarks')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')