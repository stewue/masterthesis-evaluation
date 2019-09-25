import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv')
data['measurementWarmupRatio'] = data['measurementWarmupRatio'].apply(pd.to_numeric, errors='coerce')
filtered = data[data['onlyModeChanged'] == False]
filtered = filtered[filtered['onlySingleShot'] == False]

smaller = filtered[filtered['measurementWarmupRatio'] < 1]
smallerArray = smaller['measurementWarmupRatio']
smallerAll, base = np.histogram(smallerArray, bins=10000, range=[0,1], weights=np.ones(len(smallerArray)) / len(smallerArray))
cumulativeAll = np.cumsum(smallerAll)
plt.plot(base[:-1], cumulativeAll, label="all")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.ylabel('percentage')
plt.xlabel('measurementWarmupRatio')
#plt.show()
plt.savefig('export.png')