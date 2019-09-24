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
plt.xlabel('executionTimePercentage')
plt.ylabel('cumulativePercentage')
plt.xticks(np.arange(0, 1.1, step=0.1))
#plt.show()
plt.savefig('export.png')