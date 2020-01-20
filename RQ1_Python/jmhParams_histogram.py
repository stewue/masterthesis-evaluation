import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain-header.csv')

filtered = data[data['jmhParamCount'] > 0]
values = filtered['jmhParamCount']
valuesAll, base = np.histogram(values, bins=[1, 2, 3, 4, 5, 6, 7, 10], weights=np.ones(len(values)) / len(values))
cumulativeAll = np.cumsum(valuesAll)
x = ["1", "2", "3", "4", "5", "6", "7-10"]

fig = plt.figure()
total = values.size

# absolute
ax1 = fig.add_subplot()
ax1.bar(x, valuesAll * total)
ax1.set_ylabel('# benchmarks')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.bar(x, valuesAll)
ax2.set_ylabel('# benchmarks [%]')

ax1.set_xlabel('# JMH parameters')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\jmhParams_histogram.pdf')

e1 = values[values <= 1]
print("=1: " + str(e1.size / total))
print("=1: " + str(e1.size))