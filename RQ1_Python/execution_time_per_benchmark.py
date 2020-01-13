import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
totalTime = data['executionTime'] * data['parameterizationCombinations'] / 60

all, base = np.histogram(totalTime, bins=1000, range=[0,30], weights=np.ones(len(totalTime)) / len(totalTime))
cumulative = np.cumsum(all)
plt.plot(base[:-1], cumulative)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('execution time [min]')
plt.ylabel('cumulative probability')
plt.yticks(np.arange(0,0.91,0.1))
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\execution_time_per_benchmark.pdf')

print("max: " + str(np.max(totalTime)))
print("median: " + str(np.median(totalTime)))