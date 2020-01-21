import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain-header.csv')

def update(df):
    if df['jmhParamCount'] == 0:
        return 0
    else:
        return df['parameterizationCombinations']

updated = data.apply(update, axis=1)
values = updated[updated > 1]

valuesAll, base = np.histogram(values, bins=29, range=[2, 30], weights=np.ones(len(values)) / len(values))
cumulativeAll = np.cumsum(valuesAll)

fig = plt.figure()
total = values.size

# absolute
ax1 = fig.add_subplot()
ax1.plot(base[:-1], cumulativeAll * total)
ax1.set_ylabel('# benchmarks')
ax1.set_ylim(0, total)

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.plot(base[:-1], cumulativeAll)
ax2.set_ylabel('# benchmarks [cumulative %]')
ax2.set_ylim(0, 1)

plt.xticks([2, 5, 10, 15, 20, 25, 30])
ax1.set_xlabel('# parameterization combinations')
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\parameterization_combinations.pdf')

s10 = values[values <= 10]
print("<=10: " + str(s10.size / total))
print("<=10: " + str(s10.size))
print("total: " + str(total))