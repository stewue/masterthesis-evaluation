import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain-header.csv')

values = data['numberOfLines']
valuesAll, base = np.histogram(values, bins=15, range=[1, 16], weights=np.ones(len(values)) / len(values))
cumulativeAll = np.cumsum(valuesAll)
x = np.arange(1, 16, 1)

fig = plt.figure()
total = values.size

# absolute
ax1 = fig.add_subplot()
ax1.plot(base[:-1], cumulativeAll * total)
ax1.set_ylabel('# benchmarks')
ax1.set_ylim(0, total)

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.plot(base[:-1], cumulativeAll)
ax2.set_ylabel('# benchmarks [cumulative %]')
ax2.set_ylim(0, 1)

ax1.set_xlabel('# lines')
ax1.set_xticks(np.arange(1, 16, 1))
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\method_number_of_lines.pdf')

e1 = values[values == 1]
print("=1: " + str(e1.size / total))
print("=1: " + str(e1.size))

s10 = values[values < 10]
print("<10: " + str(s10.size / total))
print("<10: " + str(s10.size))