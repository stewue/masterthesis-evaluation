import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\javaversion.csv', dtype={'version' : 'str'})

fig = plt.figure()
total = data['countShortLived'].sum() + data['countLongLived'].sum()

# absolute
ax1 = fig.add_subplot()
plt.bar(data['version'], data['countShortLived'], label="short-lived projects",  color="green", bottom=data['countLongLived'])
plt.bar(data['version'], data['countLongLived'], label="long-lived projects", color="orange")
ax1.set_ylabel('# projects')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
plt.bar(data['version'], data['countShortLived'] / total, label="short-lived projects",  color="green", bottom=data['countLongLived'] / total)
plt.bar(data['version'], data['countLongLived'] / total, label="long-lived projects", color="orange")
ax2.set_ylabel('# projects [%]')

ax1.set_xlabel('Java version')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\javaTarget_version.pdf')