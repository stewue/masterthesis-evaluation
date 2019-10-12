import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
data['totalTime'] = data['executionTime'] * data['parameterizationCombinations'] / 60

grouped = data.groupby('project')

# all
array = []
for project, benchmarks in grouped:
    array.append(benchmarks['totalTime'].sum()/60)

all, base = np.histogram(array, bins=11, range=[0,12], weights=np.ones(len(array)) / len(array))
cumulative = np.cumsum(all)
plt.plot(base[:-1], cumulative)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('execution time of test suite [hours]')
plt.ylabel('cumulative probability')
plt.xticks(np.arange(0,13,1))
plt.tight_layout()
#plt.show()
plt.savefig('export.png')

# under 1 hour
# array = []
# for project, benchmarks in grouped:
#     array.append(benchmarks['totalTime'].sum())
#
# all, base = np.histogram(array, bins=600, range=[0,60], weights=np.ones(len(array)) / len(array))
# cumulative = np.cumsum(all)
# plt.plot(base[:-1], cumulative)
#
# plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
# plt.xlabel('execution time of test suite [min]')
# plt.ylabel('cumulative probability')
# plt.xticks(np.arange(0,61,10))
# plt.tight_layout()
# #plt.show()
# plt.savefig('export.png')

print("min: " + str(np.min(array)))
print("max: " + str(np.max(array)))
print("median: " + str(np.median(array)))