import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
totalTime = data['executionTime'] * data['parameterizationCombinations'] / 60

all, base = np.histogram(totalTime, bins=1000, range=[0, 30], weights=np.ones(len(totalTime)) / len(totalTime))
cumulative = np.cumsum(all)

fig = plt.figure()
total = totalTime.shape[0]

# absolute
ax1 = fig.add_subplot()
ax1.plot(base[:-1], cumulative * total)
ax1.set_ylabel('# benchmarks')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.plot(base[:-1], cumulative)
ax2.set_ylabel('# benchmarks [cumulative %]')

ax1.set_xlabel('execution time [min]')
plt.yticks(np.arange(0, 0.91, 0.1))
plt.tight_layout()
#plt.show()
#plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\execution_time_per_benchmark.pdf')

print("max: " + str(np.max(totalTime)))
print("median: " + str(np.median(totalTime)))
print("total: " + str(total))

s10 = totalTime[totalTime < 10]
print("<10min: " + str(len(s10) / total))
print("<10min: " + str(len(s10)))

s30 = totalTime[totalTime < 30]
print("<30min: " + str(len(s30) / total))
print("<30min: " + str(len(s30)))