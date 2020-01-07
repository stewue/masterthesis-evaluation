import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\configurationchanged.csv')
values = data['numberOfChanges']

valuesAll, base = np.histogram(values, bins=[0,1,2,3,4], weights=np.ones(len(values)) / len(values))
x = np.arange(4)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1,1))
label = ('0', '1', '2', '3')
plt.bar(x, valuesAll)
plt.ylim(0,0.013)
plt.xticks(x, label)
plt.xlabel('# configuration changes')
plt.ylabel('probability')
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')

somewhereChanged = values[values > 0]
print("at least one configuration change: " + str(somewhereChanged.size) + " (" + str(somewhereChanged.size / values.size) + "%)")
print("average configuration change: " + str(values.mean()))
print("std configuration change: " + str(values.std()))