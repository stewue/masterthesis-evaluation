import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
data['measurementWarmupRatio'] = data['measurementWarmupRatio'].apply(pd.to_numeric, errors='coerce')
filtered = data[data['onlyModeChanged'] == False]
filtered = filtered[filtered['onlySingleShot'] == False]

larger = filtered[filtered['measurementWarmupRatio'] > 1]
largerArray = larger['measurementWarmupRatio']
largerAll, base = np.histogram(largerArray, bins=10000, range=[1,25], weights=np.ones(len(largerArray)) / len(largerArray))
cumulativeAll = np.cumsum(largerAll)
plt.plot(base[:-1], cumulativeAll, label="all")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.ylabel('cumulative probability')
plt.xlabel('$\mathit{measurementWarmupRatio}$')
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')

print(len(largerArray))