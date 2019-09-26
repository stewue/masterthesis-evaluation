import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('D:\\mp\\current-merged-isMain-header.csv')

def update(df):
    if df['jmhParamCount'] == 0:
        return 0
    else:
        return df['parametrizationCombinations']

updated = data.apply(update,axis=1)
values = updated[updated > 0]

valuesAll, base = np.histogram(values, bins=30, range=[1,30], weights=np.ones(len(values)) / len(values))
cumulativeAll = np.cumsum(valuesAll)

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.plot(base[:-1], cumulativeAll, label="all")
plt.xticks([1,5,10,15,20,25,30])
plt.ylim(0,1)
plt.yticks(np.arange(0,1.1,0.1))
plt.xlabel('numberOfParametrizationCombinations')
plt.ylabel('cumulativePercentage')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')