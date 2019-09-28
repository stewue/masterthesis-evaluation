import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\mp\\current-merged-isMain-header.csv')

filtered = data[np.logical_not(np.isnan(data['warmupForks']))]
values = filtered['warmupForks']

valuesAll, base = np.histogram(values, bins=[0,1,2,3], weights=np.ones(len(values)) / len(values))
x = np.arange(3)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
label = ('0', '1', '2')
plt.bar(x, valuesAll)
plt.xticks(x, label)
plt.xlabel('numberOfWarmupForks')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')