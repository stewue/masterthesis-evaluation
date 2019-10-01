import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversion.csv', dtype={'version' : 'str'})

plt.bar(data['version'], data['countLongLived'], label="long-lived", color="orange")
plt.bar(data['version'], data['countShortLived'], label="short-lived",  color="green", bottom=data['countLongLived'])
plt.xticks(rotation='vertical')
plt.xlabel('JMH version')
plt.ylabel('# projects')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('export.png')