import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\mp\\current-merged-isMain-header.csv')

values = data['numberOfLines']
valuesAll, base = np.histogram(values, bins=15, range=[1,16], weights=np.ones(len(values)) / len(values))
cumulativeAll = np.cumsum(valuesAll)
x = np.arange(1,16,1)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.plot(base[:-1], cumulativeAll, label="all")
plt.xlabel('numberOfLines')
plt.ylim(0,1)
plt.yticks(np.arange(0,1.1,0.1))
plt.xticks(np.arange(1,16,1))
plt.tight_layout()
#plt.show()
plt.savefig('export.png')