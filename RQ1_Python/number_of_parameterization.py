import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\mp\\current-merged-isMain-header.csv')
#data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain-header.csv')

grouped = data.groupby('project')

array = []
for project, benchmarks in grouped:
    array.append(benchmarks['parameterizationCombinations'].sum())

all, base = np.histogram(array, bins=[1, 5, 10, 25, 50, 75, 100, 200, 20000])

label = ('1-4', '5-9', '10-24', '25-49', '50-74', '75-99', '100-199', '200+')
x = np.arange(8)

fig = plt.figure()
ymax = 275
total = len(array)

# absolute
ax1 = fig.add_subplot()
ax1.bar(x, all)
ax1.set_ylim(0, ymax)
ax1.set_ylabel('# projects')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.bar(x, np.divide(all, total))
ax2.set_ylim(0, ymax / total)
ax2.set_ylabel('# projects [%]')

ax1.set_xlabel('# parameterization combinations')
plt.xticks(x, label)
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\number_of_parameterizations_per_project.pdf')

print("average: " + str(np.average(array)))
print("std: " + str(np.std(array)))
print("median: " + str(np.median(array)))
print("max: " + str(np.max(array)))
print("total: " + str(total))