import matplotlib.pyplot as plt
import pandas as pd
from matplotlib.ticker import PercentFormatter

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\javaversionchange.csv', dtype={'version' : 'str'})

allCount = data['numberOfChangesLongLived'].sum() + data['numberOfChangesShortLived'].sum()

numberOfChangesLongLived = data['numberOfChangesLongLived'] / allCount
numberOfChangesShortLived = data['numberOfChangesShortLived'] / allCount

plt.bar(data['count'], numberOfChangesShortLived, label="short-lived projects",  color="green", bottom=numberOfChangesLongLived)
plt.bar(data['count'], numberOfChangesLongLived, label="long-lived projects", color="orange")
plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.xlabel('# Java version updates')
plt.ylabel('probability')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')