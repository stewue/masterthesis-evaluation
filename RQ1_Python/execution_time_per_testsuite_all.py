import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
data['totalTime'] = data['executionTime'] * data['parameterizationCombinations'] / 60

grouped = data.groupby('project')

array = []
for project, benchmarks in grouped:
    array.append(benchmarks['totalTime'].sum() / 60)

all, base = np.histogram(array, bins=1000, range=[0, 12], weights=np.ones(len(array)) / len(array))
cumulative = np.cumsum(all)

fig = plt.figure()
total = len(array)

# absolute
ax1 = fig.add_subplot()
ax1.plot(base[:-1], cumulative * total)
ax1.set_ylabel('# projects')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.plot(base[:-1], cumulative)
ax2.set_ylabel('# projects [cumulative %]')

ax1.set_xlabel('execution time [hours]')
plt.xticks(np.arange(0, 13, 1))
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\execution_time_per_testsuite_all.pdf')

print("min: " + str(np.min(array)))
print("max: " + str(np.max(array)))
print("median: " + str(np.median(array)))
print("total: " + str(total))

l3 = list(filter(lambda x: x > 3, array))
print(">3h: " + str(len(l3) / len(array)))

l12 = list(filter(lambda x: x > 12, array))
print(">12h: " + str(len(l12) / len(array)))