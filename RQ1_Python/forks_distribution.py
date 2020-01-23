import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain-header.csv')

filtered = data[np.logical_not(np.isnan(data['forks']))]
values = filtered['forks']

valuesAll, base = np.histogram(values, bins=[0,1,2,3,4,5,10,20,1001], weights=np.ones(len(values)) / len(values))
x = np.arange(8)

fig = plt.figure()
total = len(values)

# absolute
ax1 = fig.add_subplot()
ax1.bar(x, valuesAll * total)
ax1.set_ylabel('# benchmarks')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.bar(x, valuesAll)
ax2.set_ylabel('# benchmarks [%]')

label = ('0', '1', '2', '3', '4', '5', '10', '11-1000')
plt.xticks(x, label)
ax1.set_xlabel('# forks')
plt.tight_layout()
#plt.show()
#plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\forks_distribution.pdf')

print("total: " + str(total))
print(valuesAll * total)