import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
data['measurementWarmupRatio'] = data['measurementWarmupRatio'].apply(pd.to_numeric, errors='coerce')
filtered = data[data['onlyModeChanged'] == False]
filtered = filtered[filtered['onlySingleShot'] == False]
filtered = filtered[filtered['measurementWarmupRatio'] < 1]

array = filtered['warmupTime'] / filtered['executionTime']

all, base = np.histogram(array, bins=600, range=[0.5, 1], weights=np.ones(len(array)) / len(array))
cumulative = np.cumsum(all)

fig = plt.figure()
total = len(array)

# absolute
ax1 = fig.add_subplot()
ax1.plot(base[:-1], cumulative * total)
ax1.set_ylabel('# benchmarks')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.plot(base[:-1], cumulative)
ax2.set_ylabel('# benchmarks [cumulative %]')

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.gca().xaxis.set_major_formatter(PercentFormatter(1))
ax1.set_xlabel('$\mathit{warmupProportion}$')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\warmupProportion_smaller_1.pdf')

s1 = array[np.logical_and(array > 0.66, array < 0.67)]
print("=90%: " + str(s1.size / array.size))
print("=90%: " + str(s1.size))

s2 = array[np.logical_and(array > 0.90, array < 0.91)]
print("=90%%: " + str(s2.size / array.size))
print("=90%: " + str(s2.size))