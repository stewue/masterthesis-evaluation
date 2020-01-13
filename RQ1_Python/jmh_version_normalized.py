import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionnormalized.csv', dtype={'version' : 'str'})

plt.bar(data['version'], data['normalizedCount'])
plt.xticks(rotation='vertical')
plt.xlabel('JMH version')
plt.ylabel('# projects normalized by number of month to next release')
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\images\\jmh_version_normalized.pdf')