import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\javaversionchange.csv', dtype={'version' : 'str'})

allCount = data['numberOfChangesLongLived'].sum() + data['numberOfChangesShortLived'].sum()

numberOfChangesLongLived = data['numberOfChangesLongLived'] / allCount
numberOfChangesShortLived = data['numberOfChangesShortLived'] / allCount

fig = plt.figure()

# absolute
ax1 = fig.add_subplot()
ax1.bar(data['count'], numberOfChangesShortLived * allCount, label="short-lived projects",  color="green", bottom=numberOfChangesLongLived * allCount)
ax1.bar(data['count'], numberOfChangesLongLived * allCount, label="long-lived projects", color="orange")
ax1.set_ylabel('# projects')

# relative
ax2 = ax1.twinx()
plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
ax2.bar(data['count'], numberOfChangesShortLived, label="short-lived projects",  color="green", bottom=numberOfChangesLongLived)
ax2.bar(data['count'], numberOfChangesLongLived, label="long-lived projects", color="orange")
ax2.set_ylabel('# projects [%]')

ax1.set_xlabel('# Java version updates')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\java_version_change.pdf')