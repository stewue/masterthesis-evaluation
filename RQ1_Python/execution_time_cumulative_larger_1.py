import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')

filtered = data[data['onlyModeChanged'] == False]
changed = filtered[filtered['onlySingleShot'] == False]
changed2 = changed[changed['executionTimePercentage'] > 1]
all = changed2['executionTimePercentage'] - 1

valuesAll, base = np.histogram(all, bins=100000, range=[0.1, 11000], weights=np.ones(len(all)) / len(all))
cumulativeAll = np.cumsum(valuesAll)

fig = plt.figure()
totalAll = changed.shape[0]
totalSelected = len(all)

proportion = totalSelected / totalAll

# absolute
ax1 = fig.add_subplot()
ax1.plot(base[:-1], cumulativeAll * totalSelected)
ax1.set_ylabel('# benchmarks')
ax1.set_xscale('log')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
plt.plot(base[:-1], cumulativeAll * proportion)
ax2.set_ylabel('# benchmarks [cumulative %]')
ax2.set_xscale('log')

ax1.set_xlabel('change rate of the execution time')
plt.xticks([0.1, 1, 10, 100, 1000, 10000], ["+10%", "+100%", "+1'000%", "+10'000%", "+100'000%", "+1'000'000%"])
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\execution_time_cumulative_larger_1.pdf')

l1 = all[all > 0]
print(">1: " + str(l1.size / totalAll))
print(">1: " + str(l1.size))

l10 = all[all > 9]
print(">10: " + str(l10.size / totalAll))
print(">10: " + str(l10.size))