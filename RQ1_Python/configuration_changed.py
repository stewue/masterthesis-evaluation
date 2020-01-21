import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\configurationchanged.csv')
values = data['numberOfChanges']

valuesAll, base = np.histogram(values, bins=[0, 1, 2, 3, 4], weights=np.ones(len(values)) / len(values))
x = np.arange(4)

fig = plt.figure()
total = values.shape[0]
ymax = 250

# absolute
ax1 = fig.add_subplot()
ax1.bar(x, valuesAll * total)
ax1.set_ylabel('# benchmarks')
ax1.set_ylim(0, ymax)

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 1))
ax2.bar(x, valuesAll)
ax2.set_ylabel('# benchmarks [%]')
ax2.set_ylim(0, ymax / total)

label = ('0', '1', '2', '3')
plt.xticks(x, label)
ax1.set_xlabel('# configuration changes')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\configuration_changed.pdf')

somewhereChanged = values[values > 0]
print("at least one configuration change: " + str(somewhereChanged.size) + " (" + str(somewhereChanged.size / values.size) + "%)")
print("average configuration change: " + str(values.mean()))
print("std configuration change: " + str(values.std()))
print("total: " + str(total))