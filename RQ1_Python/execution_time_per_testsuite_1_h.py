import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
data['totalTime'] = data['executionTime'] * data['parameterizationCombinations'] / 60

grouped = data.groupby('project')

array = []
for project, benchmarks in grouped:
    array.append(benchmarks['totalTime'].sum())

all, base = np.histogram(array, bins=600, range=[0,60], weights=np.ones(len(array)) / len(array))
cumulative = np.cumsum(all)
plt.plot(base[:-1], cumulative)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('execution time [min]')
plt.ylabel('cumulative probability')
plt.xticks(np.arange(0,61,10))
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\execution_time_per_testsuite_1_hour.pdf')