import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversion.csv', dtype={'version' : 'str'})

fig = plt.figure()
total = data['countShortLived'].sum() + data['countLongLived'].sum()

# absolute
ax1 = fig.add_subplot()
ax1.bar(data['version'], data['countShortLived'], label="short-lived projects",  color="green", bottom=data['countLongLived'])
ax1.bar(data['version'], data['countLongLived'], label="long-lived projects", color="orange")
ax1.set_ylabel('# projects')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.bar(data['version'], data['countShortLived'] / total, color="green", bottom=data['countLongLived'] / total)
ax2.bar(data['version'], data['countLongLived'] / total, color="orange")
ax2.set_ylabel('# projects [%]')

ax1.set_xticklabels(data['version'], rotation='vertical')
ax1.set_xlabel('JMH version')
ax1.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\jmh_version.pdf')