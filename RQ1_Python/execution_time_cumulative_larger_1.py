import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')

filtered = data[data['executionTimePercentage'] > 1]
all = filtered['executionTimePercentage']

valuesAll, base = np.histogram(all, bins=100000, range=[1,11000], weights=np.ones(len(all)) / len(all))
cumulativeAll = np.cumsum(valuesAll)
plt.plot(base[:-1], cumulativeAll, label="all")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('$\mathit{executionTimeRatio}$')
plt.ylabel('cumulative probability')
plt.xscale('log')
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')

l10 = all[all > 10]
print(">10: " + str(l10.size / all.size))