import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionchange.csv')

filtered = data[data['numberOfChanges'] > 0]
values = filtered['averageChangeTime']

valuesAll, base = np.histogram(values, bins=30, range=[0,2.55], weights=np.ones(len(values)) / len(values))
cumulativeAll = np.cumsum(valuesAll)
plt.plot(base[:-1], cumulativeAll, label="all")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('averageChangeTime [in years]')
plt.ylabel('cumulativePercentage')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')