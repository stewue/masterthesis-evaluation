import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')

filtered = data[data['executionTimePercentage'] < 1]
all = filtered['executionTimePercentage']

valuesAll, base = np.histogram(all, bins=1000, range=[0,1], weights=np.ones(len(all)) / len(all))
cumulativeAll = np.cumsum(valuesAll)
plt.plot(base[:-1], cumulativeAll, label="all")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('$\mathit{executionTimeRatio}$')
plt.ylabel('cumulative probability')
plt.xticks(np.arange(0, 1.1, step=0.1))
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\execution_time_cumulative_smaller_1.pdf')

s4 = all[all < 0.25]
print("<0.25: " + str(s4.size / all.size))

s10 = all[all < 0.1]
print("<0.1: " + str(s10.size / all.size))