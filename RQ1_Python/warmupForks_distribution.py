import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain-header.csv')

filtered = data[np.logical_not(np.isnan(data['warmupForks']))]
values = filtered['warmupForks']

valuesAll, base = np.histogram(values, bins=[0, 1, 2, 3], weights=np.ones(len(values)) / len(values))
x = np.arange(3)

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

label = ('0', '1', '2')
plt.xticks(x, label)
ax1.set_xlabel('# warmup forks')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\warmupForks_distribution.pdf')