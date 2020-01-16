import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')

filtered = data[data['executionTimePercentage'] < 1]
all = filtered['executionTimePercentage']

valuesAll, base = np.histogram(all, bins=1000, range=[0, 1], weights=np.ones(len(all)) / len(all))
cumulativeAll = np.cumsum(valuesAll)

fig = plt.figure()
total = len(all)

# absolute
ax1 = fig.add_subplot()
ax1.plot(base[:-1], cumulativeAll * total)
ax1.set_ylabel('# benchmarks')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
plt.plot(base[:-1], cumulativeAll)
ax2.set_ylabel('# benchmarks [cumulative %]')

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
ax1.set_xlabel('change rate of the execution time')
plt.xticks([0, 0.25, 0.5, 0.75, 1], ["-100%", "-75%", "-50%", "-25%", "0%"])
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\execution_time_cumulative_smaller_1.pdf')

s4 = all[all < 0.25]
print("<0.25: " + str(s4.size / all.size))
print("<0.25: " + str(s4.size))

s10 = all[all < 0.1]
print("<0.1: " + str(s10.size / all.size))
print("<0.1: " + str(s10.size))